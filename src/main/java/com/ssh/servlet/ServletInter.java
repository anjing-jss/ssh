package com.ssh.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletInter {
  void service(HttpServletRequest request, HttpServletResponse response);
}
