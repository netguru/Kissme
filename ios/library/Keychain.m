
#import "Keychain.h"
#import "KeychainQuery.h"

NSString *const kKeychainErrorDomain = @"com.netguru";
NSString *const kKeychainAccountKey = @"acct";
NSString *const kKeychainCreatedAtKey = @"cdat";
NSString *const kKeychainClassKey = @"labl";
NSString *const kKeychainDescriptionKey = @"desc";
NSString *const kKeychainLabelKey = @"labl";
NSString *const kKeychainLastModifiedKey = @"mdat";
NSString *const kKeychainWhereKey = @"svce";

@implementation Keychain

+ (nullable NSString *)passwordForService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    [query fetch];
    return query.password;
}

+ (nullable NSData *)passwordDataForService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    [query fetch];
    return query.passwordData;
}

+ (BOOL)deletePasswordForService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    return [query deleteItem];
}

+ (BOOL)setPassword:(NSString *)password forService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    query.password = password;
    return [query save];
}

+ (BOOL)setPasswordData:(NSData *)password forService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    query.passwordData = password;
    return [query save];
}

+ (nullable NSArray *)accountsForService:(nullable NSString *)serviceName {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    return [query fetchAll];
}

+ (BOOL)containsForService:(NSString *)serviceName account:(NSString *)account {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    query.service = serviceName;
    query.account = account;
    return [query contains];
}

+ (BOOL)clear {
    KeychainQuery *query = [[KeychainQuery alloc] init];
    return [query clear];
}

@end
