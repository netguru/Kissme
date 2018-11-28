
#import <Foundation/Foundation.h>
#import "Keychain.h"
#import "KeychainQuery.h"

typedef NS_ENUM(OSStatus, SAMKeychainErrorCode) {
	KeychainErrorBadArguments = -1001,
};

extern NSString *const kKeychainErrorDomain;
extern NSString *const kKeychainAccountKey;
extern NSString *const kKeychainCreatedAtKey;
extern NSString *const kKeychainClassKey;
extern NSString *const kKeychainDescriptionKey;
extern NSString *const kKeychainLabelKey;
extern NSString *const kKeychainLastModifiedKey;
extern NSString *const kKeychainWhereKey;

@interface Keychain : NSObject

+ (nullable NSString *)passwordForService:(NSString *)serviceName account:(NSString *)account;

+ (nullable NSData *)passwordDataForService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)deletePasswordForService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)setPassword:(NSString *)password forService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)setPasswordData:(NSData *)password forService:(NSString *)serviceName account:(NSString *)account;

+ (nullable NSArray<NSDictionary<NSString *, id> *> *)accountsForService:(nullable NSString *)serviceName;

+ (BOOL)clearKeychainForService:(NSString *)serviceName account:(NSString *)account;

@end
