//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: HelloAnnotation.java
//

#include "J2ObjC_header.h"

#pragma push_macro("INCLUDE_ALL_HelloAnnotation")
#ifdef RESTRICT_HelloAnnotation
#define INCLUDE_ALL_HelloAnnotation 0
#else
#define INCLUDE_ALL_HelloAnnotation 1
#endif
#undef RESTRICT_HelloAnnotation

#if !defined (HelloAnnotation_) && (INCLUDE_ALL_HelloAnnotation || defined(INCLUDE_HelloAnnotation))
#define HelloAnnotation_

@class IOSObjectArray;

@interface HelloAnnotation : NSObject

#pragma mark Public

- (instancetype)init;

+ (void)mainWithNSStringArray:(IOSObjectArray *)args;

@end

J2OBJC_EMPTY_STATIC_INIT(HelloAnnotation)

FOUNDATION_EXPORT void HelloAnnotation_init(HelloAnnotation *self);

FOUNDATION_EXPORT HelloAnnotation *new_HelloAnnotation_init() NS_RETURNS_RETAINED;

FOUNDATION_EXPORT HelloAnnotation *create_HelloAnnotation_init();

FOUNDATION_EXPORT void HelloAnnotation_mainWithNSStringArray_(IOSObjectArray *args);

J2OBJC_TYPE_LITERAL_HEADER(HelloAnnotation)

#endif

#pragma pop_macro("INCLUDE_ALL_HelloAnnotation")
