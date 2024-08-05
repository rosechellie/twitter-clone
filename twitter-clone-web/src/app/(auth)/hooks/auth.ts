

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { jwtDecode } from 'jwt-decode';

interface JwtPayload {
  exp: number;
}

export const useAuth = (/*to_url: string*/) => {
  const router = useRouter();

  useEffect(() => {
    const token = getCookie('jwt');
    console.log(token);

    if(!token) {
      console.log('!token');
      router.push('/login');
      return;
    }

    try {
      const decoded: JwtPayload = jwtDecode(token);
      const currentTime = Date.now() / 1000;
      if (decoded.exp < currentTime) {
        console.log('decoded.exp < currentTime');
        router.push('/login');
      } else {
        console.log('!decoded.exp < currentTime');
        // router.push(to_url);
        router.push('/home');
      }
    } catch (error) {
      console.error("Error decoding token: ", error);
      router.push('/login');
    }

  }, [router]);
}

// Helper function to get the cookie
export const getCookie = (name: string): string | undefined => {
  const value = `; ${document.cookie}`;
  console.log(value);
  const parts = value.split(`; ${name}=`);
  console.log(parts);
  if (parts.length === 2) return parts.pop()?.split(';').shift();
  return undefined;
}