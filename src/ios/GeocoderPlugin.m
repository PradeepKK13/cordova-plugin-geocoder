#import "GeocoderPlugin.h"
#import <CoreLocation/CoreLocation.h>

@implementation GeocoderPlugin {
    CLGeocoder* _geocoder;
}

- (void)pluginInitialize {
    _geocoder = [[CLGeocoder alloc] init];
}

- (void)geocode:(CDVInvokedUrlCommand *)command {
    NSString* locationName = [command.arguments objectAtIndex:0];
    NSDictionary* options = [command.arguments objectAtIndex:1];

    [_geocoder geocodeAddressString:locationName completionHandler:^(NSArray *placemarks, NSError *error) {
        CDVPluginResult *pluginResult;
        if (error != nil) {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription];
        } else {
            NSMutableArray* results = [[NSMutableArray alloc] init];
            for (CLPlacemark* placemark in placemarks) {
                [results addObject:[self placemarkToDictionary:placemark]];
            }
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:results];
        }
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}

- (void)reverseGeocode:(CDVInvokedUrlCommand *)command {
}

- (NSDictionary*)placemarkToDictionary:(CLPlacemark *)placemark {
    return @{
        @"latitude": [NSNumber numberWithDouble:placemark.location.coordinate.latitude],
        @"longitude": [NSNumber numberWithDouble:placemark.location.coordinate.longitude],

        @"country": placemark.country ? placemark.country : @"",
        @"countryCode": placemark.ISOcountryCode ? placemark.ISOcountryCode : @"",
        @"postalCode": placemark.postalCode ? placemark.postalCode : @"",
        @"administrativeArea": placemark.administrativeArea ? placemark.administrativeArea : @"",
        @"subAdministrativeArea": placemark.subAdministrativeArea ? placemark.subAdministrativeArea : @"",
        @"thoroughfare": placemark.thoroughfare ? placemark.thoroughfare : @"",
        @"subThoroughfare": placemark.subThoroughfare ? placemark.subThoroughfare : @"",
        @"locality": placemark.locality ? placemark.locality : @"",
        @"subLocality": placemark.subLocality ? placemark.subLocality : @"",

        @"formattedAddress": [placemark.addressDictionary[@"FormattedAddressLines"] componentsJoinedByString:@", "]

        // @"name": placemark.name ? placemark.name : @""
    };
}

@end
