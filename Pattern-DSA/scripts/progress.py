#!/usr/bin/env python3
"""Simple stdlib-only DSA practice logger. Run from any working directory."""
import argparse
import csv
import datetime
import pathlib
import sys

ROOT = pathlib.Path(__file__).resolve().parents[1]
PROBLEMS = ROOT / 'tracking' / 'problems.csv'
ATTEMPTS = ROOT / 'tracking' / 'attempts.csv'
STATUSES = ['TODO', 'ATTEMPTED', 'SOLVED', 'REVIEW']

def read_rows(path):
    with path.open(newline='', encoding='utf-8') as f:
        return list(csv.DictReader(f))

def write_rows(path, rows, fields):
    with path.open('w', newline='', encoding='utf-8') as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        w.writerows(rows)

def main():
    parser = argparse.ArgumentParser(description='Track DSA practice by pattern and approach')
    sub = parser.add_subparsers(dest='cmd', required=True)
    sub.add_parser('summary')
    ls = sub.add_parser('list')
    ls.add_argument('--pattern', default='', help='e.g. intervals / stack / majority')
    lg = sub.add_parser('log')
    lg.add_argument('--id', required=True, help='LeetCode number, e.g. 56')
    lg.add_argument('--approach', required=True, help='brute, optimal, sorting, hashmap...')
    lg.add_argument('--minutes', required=True, type=float)
    lg.add_argument('--time', required=True, help='Big-O time, e.g. O(n log n)')
    lg.add_argument('--space', required=True, help='Big-O space, e.g. O(n)')
    lg.add_argument('--status', choices=STATUSES, default='ATTEMPTED')
    lg.add_argument('--notes', default='')
    lg.add_argument('--runtime-ms', default='', help='Optional observed benchmark/runtime in milliseconds')
    args = parser.parse_args()
    rows = read_rows(PROBLEMS)
    if args.cmd == 'summary':
        print(f'Overall: {len(rows)} tracked questions')
        for s in STATUSES:
            matching = sum(1 for row in rows if row['status'] == s)
            print(f'  {s:<10} {matching}')
        patterns = sorted({row['pattern'] for row in rows})
        print('By pattern:')
        for p in patterns:
            part = [row for row in rows if row['pattern'] == p]
            done = sum(row['status'] == 'SOLVED' for row in part)
            print(f'  {p:<17} {done}/{len(part)} SOLVED')
        return 0
    if args.cmd == 'list':
        for row in rows:
            if args.pattern.lower() in row['pattern'].lower():
                print(f"LC {row['leetcode_id']:>4} | {row['pattern']:<16} | {row['status']:<9} | {row['title']}")
        return 0
    if args.minutes < 0:
        parser.error('--minutes must be nonnegative')
    matches = [row for row in rows if row['leetcode_id'] == args.id]
    if not matches:
        parser.error(f'LC {args.id} is not in tracking/problems.csv; add it first')
    row = matches[0]
    today = datetime.datetime.now().astimezone().isoformat(timespec='seconds')
    log = {'timestamp': today, 'leetcode_id': args.id, 'pattern': row['pattern'],
           'title': row['title'], 'approach': args.approach, 'minutes': args.minutes,
           'time_complexity': args.time, 'space_complexity': args.space,
           'runtime_ms': args.runtime_ms, 'status': args.status, 'notes': args.notes}
    with ATTEMPTS.open('a', newline='', encoding='utf-8') as f:
        w = csv.DictWriter(f, fieldnames=list(log))
        w.writerow(log)
    row['status'] = args.status
    row['last_practiced'] = today[:10]
    row['last_approach'] = args.approach
    row['last_time_complexity'] = args.time
    row['last_space_complexity'] = args.space
    write_rows(PROBLEMS, rows, list(rows[0]))
    print(f"Logged LC {args.id} ({row['title']}), {args.approach}, {args.minutes} min, {args.status}")
    return 0

if __name__ == '__main__':
    sys.exit(main())
