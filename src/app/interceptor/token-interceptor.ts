import { inject, Injectable } from '@angular/core';
import { AuthService } from '../service/auth-service';
import { HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const token = auth.getToken();

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
