#!/bin/bash

echo *******************************Code Start*****************************
# @echo off
# set "Ymd=%date:~,4%%date:~5,2%%date:~8,2%"
# @echo on
# *******************************Code End*****************************
# *******************************新的征程*****************************
# *******************************Code Start*****************************

NOW_TIME=$(date "+%Y%m%d_%H%M%S")
echo $NOW_TIME

mysqldump --opt -h 127.0.0.1 -P 9501  -u root --password=H_twinkle_0922 htwinkle.cn.web > /root/db_backup/htwinkle.cn.web_$NOW_TIME.sql

echo *******************************Code End*****************************