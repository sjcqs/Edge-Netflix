#?/bin/bash

pcre2grep -M -f pattern2.txt client3.log | cut -d' ' -f5| sort | uniq -c
