/** @type {import('next').NextConfig} */
const nextConfig = {
  // output: 'export',
  // images: {unoptimized: true},
  experimental: {
    missingSuspenseWithCSRBailout: false,
  },
};

// module.exports = {
//   experimental: {
//     missingSuspenseWithCSRBailout: false,
//   },
// }

export default nextConfig;
