
#import <Foundation/Foundation.h>
#import "Keychain.h"

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

+ (NSDictionary<NSString *, id> *)getAllPasswordForService:(NSString *)serviceName;

+ (BOOL)containsForService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)clear;

@end
