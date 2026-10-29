#!/bin/bash
# System report script — Kuat Akberen

LOG_FILE="$(dirname "$0")/../logs/Kuat_Akberen_system_report.log"

report() {
    echo "============================================"
    echo "  Student: Kuat Akberen"
    echo "============================================"
    echo "Date and Time : $(date)"
    echo "Username      : $(whoami)"
    echo "--------------------------------------------"
    echo "OS and Kernel :"
    uname -a
    echo "--------------------------------------------"
    echo "CPU Info      :"
    if command -v lscpu &>/dev/null; then
        lscpu | grep "Model name"
    else
        sysctl -n machdep.cpu.brand_string 2>/dev/null || echo "N/A"
    fi
    echo "--------------------------------------------"
    echo "Memory Info   :"
    if command -v free &>/dev/null; then
        free -h
    else
        vm_stat 2>/dev/null || echo "N/A"
    fi
    echo "--------------------------------------------"
    echo "Disk Usage    :"
    df -h
    echo "--------------------------------------------"
    echo "Available Disk Space (/) :"
    df -h / | tail -1
    echo "============================================"
}

report | tee "$LOG_FILE"
echo ""
echo "Report saved to: $LOG_FILE"
