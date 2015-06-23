system_profiler SPUSBDataType | sed -n '/iPad/,/Serial/p' | grep "Serial Number:" | awk -F ":" '{print $2}'
