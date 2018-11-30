#import "KeychainQuery.h"
#import "Keychain.h"

@implementation KeychainQuery

@synthesize account = _account;
@synthesize service = _service;
@synthesize label = _label;
@synthesize passwordData = _passwordData;

- (BOOL)save {
    OSStatus status = 0;
	if (!self.service || !self.account || !self.passwordData) {
		return NO;
	}
	NSMutableDictionary *query = nil;
	NSMutableDictionary * searchQuery = [self query];
	status = SecItemCopyMatching((__bridge CFDictionaryRef)searchQuery, nil);
	if (status == errSecSuccess) {//item already exists, update it!
		query = [[NSMutableDictionary alloc]init];
		[query setObject:self.passwordData forKey:(__bridge id)kSecValueData];
		status = SecItemUpdate((__bridge CFDictionaryRef)(searchQuery), (__bridge CFDictionaryRef)(query));
	}else if(status == errSecItemNotFound){//item not found, create it!
		query = [self query];
		if (self.label) {
			[query setObject:self.label forKey:(__bridge id)kSecAttrLabel];
		}
		[query setObject:self.passwordData forKey:(__bridge id)kSecValueData];
		status = SecItemAdd((__bridge CFDictionaryRef)query, NULL);
	}

	return (status == errSecSuccess);
}


- (BOOL)deleteItem {
    OSStatus status = 0;
	if (!self.service || !self.account) {
		return NO;
	}

	NSMutableDictionary *query = [self query];
	status = SecItemDelete((__bridge CFDictionaryRef)query);

	return (status == errSecSuccess);
}

- (nullable NSArray *)fetchAll {
	NSMutableDictionary *query = [self query];
	[query setObject:@YES forKey:(__bridge id)kSecReturnAttributes];
	[query setObject:(__bridge id)kSecMatchLimitAll forKey:(__bridge id)kSecMatchLimit];

	CFTypeRef result = NULL;
	OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)query, &result);
	if (status != errSecSuccess) {
		return nil;
	}

	return (__bridge_transfer NSArray *)result;
}

- (BOOL)fetch {
    OSStatus status = 0;
	if (!self.service || !self.account) {
		return NO;
	}

	CFTypeRef result = NULL;
	NSMutableDictionary *query = [self query];
	[query setObject:@YES forKey:(__bridge id)kSecReturnData];
	[query setObject:(__bridge id)kSecMatchLimitOne forKey:(__bridge id)kSecMatchLimit];
	status = SecItemCopyMatching((__bridge CFDictionaryRef)query, &result);

	if (status != errSecSuccess) {
		return NO;
	}

	self.passwordData = (__bridge_transfer NSData *)result;
	return YES;
}

- (void)setPasswordObject:(id<NSCoding>)object {
	self.passwordData = [NSKeyedArchiver archivedDataWithRootObject:object];
}

- (id<NSCoding>)passwordObject {
	if ([self.passwordData length]) {
		return [NSKeyedUnarchiver unarchiveObjectWithData:self.passwordData];
	}
	return nil;
}

- (void)setPassword:(NSString *)password {
	self.passwordData = [password dataUsingEncoding:NSUTF8StringEncoding];
}

- (NSString *)password {
	if ([self.passwordData length]) {
		return [[NSString alloc] initWithData:self.passwordData encoding:NSUTF8StringEncoding];
	}
	return nil;
}

- (NSMutableDictionary *)query {
	NSMutableDictionary *dictionary = [NSMutableDictionary dictionaryWithCapacity:3];
	[dictionary setObject:(__bridge id)kSecClassGenericPassword forKey:(__bridge id)kSecClass];

	if (self.service) {
		[dictionary setObject:self.service forKey:(__bridge id)kSecAttrService];
	}

	if (self.account) {
		[dictionary setObject:self.account forKey:(__bridge id)kSecAttrAccount];
	}

	return dictionary;
}

- (BOOL)contains{
    if (!self.service || !self.account) {
        return NO;
    }
    
    OSStatus status = 0;
    CFTypeRef result = NULL;
    NSMutableDictionary *query = [self query];
    [query setObject:@YES forKey:(__bridge id)kSecReturnData];
    [query setObject:(__bridge id)kSecMatchLimitOne forKey:(__bridge id)kSecMatchLimit];
    status = SecItemCopyMatching((__bridge CFDictionaryRef)query, &result);
    
    if (status != errSecSuccess) {
        return NO;
    }
    
    if (result == nil) {
        return NO;
    } else {
        return YES;
    }
}

- (BOOL)clear {
    OSStatus status = 0;
    NSArray *secItemClasses = @[(__bridge id)kSecClassGenericPassword,
                                (__bridge id)kSecClassInternetPassword,
                                (__bridge id)kSecClassCertificate,
                                (__bridge id)kSecClassKey,
                                (__bridge id)kSecClassIdentity];
    
    for (id secItemClass in secItemClasses) {
        NSDictionary *spec = @{(__bridge id)kSecClass: secItemClass};
        status = SecItemDelete((__bridge CFDictionaryRef)spec);
    }
    
    if (status == errSecSuccess) {
        return TRUE;
    } else {
        return FALSE;
    }
}

@end
