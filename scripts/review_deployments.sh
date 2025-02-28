#!/bin/bash

# Script to review recent deployments and changes in the backend service
# This script identifies potential issues by analyzing recent Git commits and changes.

# Exit immediately if a command exits with a non-zero status
set -e

# Define the backend directory to monitor
BACKEND_DIR="backend"

# Define the number of recent commits to analyze
NUM_COMMITS=10

# Function to check recent Git commits
function check_recent_commits() {
    echo "Checking the last $NUM_COMMITS commits for recent changes..."
    git log -n $NUM_COMMITS --pretty=format:"%h - %an, %ar : %s" -- $BACKEND_DIR
}

# Function to identify changes in the backend directory
function check_backend_changes() {
    echo "Identifying recent changes in the $BACKEND_DIR directory..."
    git diff HEAD~$NUM_COMMITS --stat -- $BACKEND_DIR
}

# Function to summarize potential issues
function summarize_issues() {
    echo "Summarizing potential issues based on recent changes..."
    echo "Note: Look for changes in critical files such as configuration, database migrations, or health check logic."
    echo "If any suspicious changes are found, investigate further."
}

# Main script execution
echo "=== Reviewing Recent Deployments and Changes ==="
check_recent_commits
echo
check_backend_changes
echo
summarize_issues
echo "=== Review Complete ==="