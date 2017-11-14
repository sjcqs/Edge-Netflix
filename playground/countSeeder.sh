#?/bin/bash

pcre2grep -M -f pattern.txt "$1" | grep "INFO: [A-Z0-9]\{20\}" | cut -d' ' -f2 | sort | uniq -c
