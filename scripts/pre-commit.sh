#!/bin/bash

# Run spotlessApply to format the code and capture the output
apply_output=$(./gradlew spotlessApply 2>&1)

# Check if spotlessApply succeeded
if [ $? -ne 0 ]; then
  echo "spotlessApply failed with the following error:"
  echo "$apply_output"
  exit 1
fi

# Run spotlessCheck to verify the formatting and capture the output
check_output=$(./gradlew spotlessCheck 2>&1)

# Check if spotlessCheck succeeded
if [ $? -ne 0 ]; then
  echo "spotlessCheck failed with the following error:"
  echo "$check_output"
  exit 1
fi

echo "Spotless formatting and check passed successfully!"
