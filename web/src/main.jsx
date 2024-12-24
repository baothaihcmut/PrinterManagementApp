import { createRoot } from 'react-dom/client'
// Import our custom CSS
import './scss/styles.scss'

// Import all of Bootstrap's JS
import App from './App.jsx'


createRoot(document.getElementById('root')).render(
  // <StrictMode>
    <App />,
  // </StrictMode>,
)
