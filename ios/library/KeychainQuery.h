
#import <Foundation/Foundation.h>

@interface KeychainQuery : NSObject

@property (nonatomic, copy, nullable) NSString *account;
@property (nonatomic, copy, nullable) NSString *service;
@property (nonatomic, copy, nullable) NSString *label;
@property (nonatomic, copy, nullable) NSData *passwordData;
@property (nonatomic, copy, nullable) id<NSCoding> passwordObject;
@property (nonatomic, copy, nullable) NSString *password;

- (BOOL)save;

- (BOOL)deleteItem;

- (nullable NSArray<NSDictionary<NSString *, id> *> *)fetchAll;

- (BOOL)fetch;

- (BOOL)clear;

- (BOOL)contains;

@end
