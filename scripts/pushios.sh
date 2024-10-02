#!/bin/sh
VERSION=$1;
echo "Pushing version $VERSION"
echo "------------------------"
cd ..


PODSPEC=shared/build/cocoapods/publish/release/LicenseManager.podspec
XCFRAMEWORK=shared/build/cocoapods/publish/release/LicenseManager.xcframework

if [ -f "$PODSPEC" ]; then
   cp -R shared/build/cocoapods/publish/release/LicenseManager.podspec LicenseManager.podspec
else
  echo "File LicenseManager.podspec not present"
  exit 1
fi

if [ -d "$XCFRAMEWORK" ]; then
   cp -R "$XCFRAMEWORK" LicenseManager.xcframework
 else
   echo "File LicenseManager.xcframework not present"
   exit 1
fi

#git checkout -b release/"$VERSION"
#git add LicenseManager.podspec
#git add LicenseManager.xcframework
#git commit -m 'Create iOS version'
#git push origin release/"$VERSION" -u
#git tag "$VERSION" -a -m "Tag version auto generated"
#git push origin --tags

# Push library
#pod repo push avenza LicenseManager.podspec