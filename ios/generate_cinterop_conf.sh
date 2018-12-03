#! /bin/bash
current_dir=`pwd`
target_file_dir="./src/main/c_interop/"
target_file_name="keychainwrapper.def"

rm -f "$target_file_dir$target_file_name"

echo "writing config to $target_file_dir$target_file_name"

mkdir -p $target_file_dir
echo \
"language = Objective-C
headers = Keychain.h KeychainQuery.h
compilerOpts = -F$current_dir
linkerOpts = -F$current_dir/library
staticLibraries = keychainwrapper.a
libraryPaths = $current_dir/library" \
>> "$target_file_dir$target_file_name"