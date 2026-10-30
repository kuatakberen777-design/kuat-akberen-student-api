#!/bin/bash
# System report script — Kuat Akberen

LOG_FILE="$(cd "$(dirname "$0")/.." && pwd)/logs/Kuat_Akberen_system_report.log"

os_name() {
    if command -v sw_vers &>/dev/null; then
        echo "$(sw_vers -productName) $(sw_vers -productVersion)"
    elif [ -r /etc/os-release ]; then
        . /etc/os-release && echo "$PRETTY_NAME"
    else
        uname -s
    fi
}

report() {
    echo "============================================"
    echo "  Student: Kuat Akberen"
    echo "============================================"
    echo "Date and Time : $(date)"
    echo "Username      : $(whoami)"
    echo "OS            : $(os_name)"
    echo "Kernel        : $(uname -sr)"
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
        echo "Total: $(($(sysctl -n hw.memsize) / 1024 / 1024 / 1024)) GB"
        vm_stat | head -5
    fi
    echo "--------------------------------------------"
    echo "Disk Usage    :"
    df -h /
    echo "--------------------------------------------"
    echo "Available Disk Space (/) : $(df -h / | awk 'NR==2 {print $4}')"
    echo "============================================"
}

report | tee -a "$LOG_FILE"
echo ""
echo "Report saved to: $LOG_FILE"
