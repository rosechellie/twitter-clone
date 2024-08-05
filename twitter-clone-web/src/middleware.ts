import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server';

export function middleware(req: NextRequest) {
  const token = req.cookies.get('jwt');
  const url = req.nextUrl.clone();

  if(url.pathname == '/') {
    if(token) {
      url.pathname = '/home';
      return NextResponse.redirect(url);
    } else {
      url.pathname = '/login';
      return NextResponse.redirect(url);
    }
  }

  if (!token && url.pathname !== '/login') {
    url.pathname = '/login';
    return NextResponse.redirect(url);
  }

  if(token && url.pathname == '/login') {
    url.pathname = '/home';
    return NextResponse.redirect(url);
  }


  return NextResponse.next();
}

export const config = {
  matcher: ['/', '/login', '/home', '/profile', '/tweets/:path*'], //Protect these routes
}