current_dir=`pwd`
target_file_path="./src/main/c_interop/keychainwrapper.def"

rm -f $target_file_path

echo "writing config to ./src/main/c_interop/keychainwrapper.def"

echo \
"language = Objective-C
headers = Keychain.h KeychainQuery.h KeychainWrapper.h
compilerOpts = -framework keychainwrapper
linkerOpts = -F$current_dir -framework keychainwrapper" \
>> $target_file_path