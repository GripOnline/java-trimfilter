# HTML whitespace filter for Java

This library provides an HTML whitespace filter that you can use to filter out unneeded whitespace from generated HTML. It does not buffer the whole page so it will not hurt your time-to-first-byte (TTFB). Contents of `<script>` / `<style>` / `<textarea>` / `<pre>` and comment tags are never trimmed.

# Example

This filter turns this

```html


<!DOCTYPE html>

<html lang="nl">

    <head>
    	<meta charset="utf-8">

	<title>Online koploper worden | Grip Online</title>

	<meta name="generator" content="e-Grip">
	<meta name="description" content="Grip Online helpt uw bedrijf bij de essentiële uitdaging om in uw branche online koploper te worden of te blijven. Van e-commerce tot corporate, van sites tot apps. Kennismaken met Grip?">



	<link rel="shortcut icon" href="/assets/grip_online/favicon.png">
	<link rel="icon" type="image/png" sizes="192x192" href="/assets/grip_online/images/default/favicon-192x192.png">
	<link rel="apple-touch-icon" type="image/png" sizes="180x180" href="/assets/grip_online/images/default/apple-touch-icon-180x180.png">
	<link rel="home" href="/" title="Homepage">

	<link rel="stylesheet" href="/assets/grip_online/css/default/screen.css">
</head>

    <body>


    <div id="container">


        <div id="header" class="site-header">

```

into this:

```html
<!DOCTYPE html>
<html lang="nl">
<head>
<meta charset="utf-8">
<title>Online koploper worden | Grip Online</title>
<meta name="generator" content="e-Grip">
<meta name="description" content="Grip Online helpt uw bedrijf bij de essentiële uitdaging om in uw branche online koploper te worden of te blijven. Van e-commerce tot corporate, van sites tot apps. Kennismaken met Grip?">
<link rel="shortcut icon" href="/assets/grip_online/favicon.png">
<link rel="icon" type="image/png" sizes="192x192" href="/assets/grip_online/images/default/favicon-192x192.png">
<link rel="apple-touch-icon" type="image/png" sizes="180x180" href="/assets/grip_online/images/default/apple-touch-icon-180x180.png">
<link rel="home" href="/" title="Homepage">
<link rel="stylesheet" href="/assets/grip_online/css/default/screen.css">
</head>
<body>
<div id="container">
<div id="header" class="site-header">
```

# Compatibility
The fiter is compatible with Servlet API 2.5 and up, including Jakarta (4.0 and up). It requires Java version 8 as minimum to run. This library only works with with single-byte character encodings and UTF-8.

# Installing

The library can be installed using Maven

```xml
<dependency>
    <groupId>nl.grip</groupId>
    <artifactId>trimfilter</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Usage
Example usage with Servlet 2.5 - 4.0:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>

  <!-- define filter -->
  <filter>
    <filter-name>trim</filter-name>
    <filter-class>nl.grip.trimfilter.TrimFilter</filter-class>
  </filter>

  <!-- define filter mapping -->
  <filter-mapping>
    <filter-name>trim</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

</web-app>
```

Example usage with Jakarta Servlet 4.0 - 6.0 (different filter class):

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app>

  <!-- define filter -->
  <filter>
    <filter-name>trim</filter-name>
    <filter-class>nl.grip.trimfilter.jakarta.TrimFilter</filter-class>
  </filter>

  <!-- define filter mapping -->
  <filter-mapping>
    <filter-name>trim</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

</web-app>
```

# Background
We like our HTML as clean as possible. When working with a template engine (such as Twig), indenting template tags can introduce a whole lot of whitespace in the output. That's why we created this filter. Other than that, most whitespace in HTML can be seen as 'waste'. It may be true that the excessive whitespace compresses quite well using gzip or brotli, but on the client side it will be sent uncompressed to the HTML parser.
