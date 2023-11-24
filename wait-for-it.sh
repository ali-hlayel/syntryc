#!/bin/bash
# wait-for-elasticsearch.sh

host="$1"
port="$2"
shift 2
cmd="$@"

timeout=30
quiet=false
strict=false

while :; do
  case "$1" in
    -t | --timeout)
      timeout="$2"
      shift 2
      ;;
    -q | --quiet)
      quiet=true
      shift
      ;;
    -s | --strict)
      strict=true
      shift
      ;;
    --)
      shift
      break
      ;;
    *)
      break
      ;;
  esac
done

start_ts=$(date +%s)
end_ts=$((start_ts + timeout))

while true; do
  response=$(curl -s -o /dev/null -w "%{http_code}" "$host:$port")

  if [ "$response" -eq 200 ]; then
    break
  fi

  if [ "$quiet" = false ]; then
    echo -n "."
  fi

  current_ts=$(date +%s)
  if [ "$strict" = true ] && [ $current_ts -ge $end_ts ]; then
    if [ "$quiet" = false ]; then
      echo " Timeout"
    fi
    exit 1
  fi

  sleep 1
done

if [ "$quiet" = false ]; then
  end_ts=$(date +%s)
  echo " Elasticsearch is ready! Elapsed time: $((end_ts - start_ts)) seconds"
fi

exec $cmd
