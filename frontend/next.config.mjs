/** @type {import('next').NextConfig} */
const nextConfig = {
  output: 'export',
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
