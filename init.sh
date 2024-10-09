#!/bin/bash


echo "Please enter your service name. Single words/abbreviations would be cooler :) ..."
echo "Or you can even use catchy names from your favorite comic/fantasy worlds..."

echo "Enter service name..."
read SERVICE_NAME

echo "Setting up your repo now..., this can take up to a minute"
sleep 1

export SMALL_CASE_NAME=$(echo "$SERVICE_NAME" | awk '{print tolower($0)}')
export UPPER_CASE_NAME=$(echo "$SERVICE_NAME" | awk '{print toupper($0)}')
export CAMEL_CASE_NAME=$(echo "$SERVICE_NAME" | awk '{print toupper(substr($0,1,1)) substr($0,2)}')

folders=("./buildSrc" "./products" "./servers")

for folder in ${folders[@]}; do
  # Update File names
  find $folder -type f -name "*" -exec sh -c 'mv "$0" "${0/kptr/${SMALL_CASE_NAME}}"' "{}" \;
  find $folder -type f -name "*" -exec sh -c 'mv "$0" "${0/KPTR/${UPPER_CASE_NAME}}"' "{}" \;
  find $folder -type f -name "*" -exec sh -c 'mv "$0" "${0/Kptr/${CAMEL_CASE_NAME}}"' "{}" \;


  # update folder names
  find $folder -type d -name "*" -exec sh -c 'mv "$0" "${0/kptr/${SMALL_CASE_NAME}}"' "{}" \;
  find $folder -type d -name "*" -exec sh -c 'mv "$0" "${0/KPTR/${UPPER_CASE_NAME}}"' "{}" \;
  find $folder -type d -name "*" -exec sh -c 'mv "$0" "${0/Kptr/${CAMEL_CASE_NAME}}"' "{}" \;

  # update files contents
  LC_ALL=C find $folder -type f -name '*' -exec sed -i '' s/kptr/${SMALL_CASE_NAME}/g {} +
  LC_ALL=C find $folder -type f -name '*' -exec sed -i '' s/KPTR/${UPPER_CASE_NAME}/g {} +
  LC_ALL=C find $folder -type f -name '*' -exec sed -i '' s/Kptr/${CAMEL_CASE_NAME}/g {} +
done


sed -i '' s/kptr/${SMALL_CASE_NAME}/g settings.gradle.kts
sed -i '' s/kptr/${SMALL_CASE_NAME}/g build.gradle.kts

echo "building the project now"
sleep 1

./gradlew clean build

echo "Repo setup is done."
