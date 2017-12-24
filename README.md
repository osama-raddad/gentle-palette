 <center><img src="https://raw.githubusercontent.com/osama-raddad/gentle-palette/master/logo.png" alt="logo"/></center>
 
<a href='https://ko-fi.com/A4763RZL' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi2.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

 [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
 [![Codacy Badge](https://api.codacy.com/project/badge/Grade/eb5b2ab070a045589f8ece28b6bd2ba8)](https://www.codacy.com/app/osama-s-raddad/gentle-palette?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=osama-raddad/gentle-palette&amp;utm_campaign=Badge_Grade)
 [![](https://jitpack.io/v/osama-raddad/gentle-palette.svg)](https://jitpack.io/#osama-raddad/gentle-palette)
 <img src="https://img.shields.io/badge/API-15%2B-brightgreen.svg"/>

# Gentle Palette

Gentle Palette is a color rounding tool is meant to work with the android palette support library or any color :wink:
it works by rounding any given hex color to its nearest color in predefined colors array (color palette).


## Requirements

Min SDK version 15

## Install
Add it in your root build.gradle at the end of repositories:

```js
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency

```js
	dependencies {
	        implementation 'com.github.osama-raddad:gentle-palette:0.1.3'
	}
```

## Usage

to use the library add this code any where :

```java
GentlePalette.generate("#FFFFFF",${Any Colors Array},this::userTheRounededColor);
```

or you can use one of my predefined color palettes :

```java
Palettes.FLAT_COLORS
Palettes.MATERIAL_COLORS
Palettes.FLUENT_COLORS
Palettes.METRO_COLORS
Palettes.SOCIAL_COLORS
```

## Contributing

We welcome contributions to FireCrasher!
* ⇄ Pull requests and ★ Stars are always welcome.

### Let me know!

I’d be really happy if you sent me links to your projects where you use my library. Just send an email to osama.s.raddad@gmail.com And do let me know if you have any questions or suggestion regarding the library. 

## License

    Copyright 2016, Osama Raddad

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





