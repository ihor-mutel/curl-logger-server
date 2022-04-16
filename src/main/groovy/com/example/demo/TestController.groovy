package com.example.demo

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

@Slf4j
@RestController
@RequestMapping("/**")
class TestController {

  @Autowired
  CurlLoggerInterceptor curlLoggerInterceptor

  @CrossOrigin
  @PostMapping()
  ResponseEntity<String> post(HttpServletRequest request) {
    String response =  new CurlLoggerInterceptor().intercept(curlLoggerInterceptor.create(request, curlLoggerInterceptor.getURL(request)))
    return new ResponseEntity<String>(response, HttpStatus.OK)
  }

  @CrossOrigin
  @GetMapping()
  ResponseEntity<String> get(HttpServletRequest request) {
    String response = new CurlLoggerInterceptor().intercept(curlLoggerInterceptor.create(request, curlLoggerInterceptor.getURL(request)))
    return new ResponseEntity<String>(response, HttpStatus.OK)
  }

  @CrossOrigin
  @PutMapping()
  ResponseEntity<String> put(HttpServletRequest request) {
    String response = new CurlLoggerInterceptor().intercept(curlLoggerInterceptor.create(request, curlLoggerInterceptor.getURL(request)))
    return new ResponseEntity<String>(response, HttpStatus.OK)
  }

  @CrossOrigin
  @DeleteMapping()
  ResponseEntity<String> delete(HttpServletRequest request) {
    String response = new CurlLoggerInterceptor().intercept(curlLoggerInterceptor.create(request, curlLoggerInterceptor.getURL(request)))
    return new ResponseEntity<String>(response, HttpStatus.OK)
  }

}

