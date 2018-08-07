#import <Cordova/CDV.h>

@interface GeocoderPlugin : CDVPlugin

- (void)geocode:(CDVInvokedUrlCommand*)command;
- (void)reverseGeocode:(CDVInvokedUrlCommand*)command;

@end
