#! /bin/bash
current_dir=$1
target_file_dir="$1/src/main/c_interop/"
target_file_name="keychainwrapper.def"

rm -f "$target_file_dir$target_file_name"

echo "writing config to $target_file_dir$target_file_name"
echo "currentdir $current_dir"
mkdir -p $target_file_dir
echo \
"language = Objective-C
headers = Keychain.h KeychainQuery.h
compilerOpts = -F$current_dir/library/keychainwrapper/bin
linkerOpts = -F$current_dir/library/keychainwrapper/bin
staticLibraries = keychainwrapper.a
libraryPaths = $current_dir/library/keychainwrapper/bin" \
>> "$target_file_dir$target_file_name"
