/** @type {import('next').NextConfig} */
const nextConfig = {
  // added for middleware.ts? start
  reactStrictMode: true,
  experimental: {
    appDir: true,
  },
  // added for middleware.ts? end

};

export default nextConfig;
