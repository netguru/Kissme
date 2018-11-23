//
//  SAMKeychainQuery.h
//  SAMKeychain
//
//  Created by Caleb Davenport on 3/19/13.
//  Copyright (c) 2013-2014 Sam Soffes. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Security/Security.h>

typedef NS_ENUM(NSUInteger, SAMKeychainQuerySynchronizationMode) {
	SAMKeychainQuerySynchronizationModeAny,
	SAMKeychainQuerySynchronizationModeNo,
	SAMKeychainQuerySynchronizationModeYes
};

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

@end
