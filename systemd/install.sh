#!/bin/bash
cp -f chat1*.service /etc/systemd/system/
systemctl daemon-reload
systemctl status chat1