/** @type {import('tailwindcss').Config} */
export default {
  prefix: 'tw-',
  content: ["./src/**/*.{html,js,jsx}"],
  theme: {
    extend: {
      colors: {
        customBlue: '#5DA5FF',
        customPurple: '#32448C',
      },
    }, 
  },
  plugins: [],
}

