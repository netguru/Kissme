
#import <Foundation/Foundation.h>
#import "Keychain.h"
#import "KeychainQuery.h"

extern NSString *const kSAMKeychainErrorDomain;
extern NSString *const kSAMKeychainAccountKey;
extern NSString *const kSAMKeychainCreatedAtKey;
extern NSString *const kSAMKeychainClassKey;
extern NSString *const kSAMKeychainDescriptionKey;
extern NSString *const kSAMKeychainLabelKey;
extern NSString *const kSAMKeychainLastModifiedKey;
extern NSString *const kSAMKeychainWhereKey;

@interface Keychain : NSObject

+ (nullable NSString *)passwordForService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)deletePasswordForService:(NSString *)serviceName account:(NSString *)account;

+ (BOOL)setPassword:(NSString *)password forService:(NSString *)serviceName account:(NSString *)account;

+ (nullable NSArray<NSDictionary<NSString *, id> *> *)allAccounts;

+ (nullable NSArray<NSDictionary<NSString *, id> *> *)accountsForService:(nullable NSString *)serviceName;

@end


