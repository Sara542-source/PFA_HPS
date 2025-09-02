/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  darkMode: 'class', // Activer le mode sombre avec la classe 'dark'
  theme: {
    extend: {
      colors: {
        primary: '#2563EB', // Bleu professionnel
        'primary-dark': '#1E40AF', // Bleu plus foncé pour hover
      },
    },
  },
  plugins: [],
}