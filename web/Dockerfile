# Step 1: Build the React app
FROM node:16 AS build

# Set working directory
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm cache clean --force
RUN npm config set registry http://registry.npmjs.org/
RUN npm install

# Copy the rest of the application
COPY . ./

# Build the React app for production
RUN npm run build

# Step 2: Serve the app with Nginx
FROM nginx:alpine

# Copy the build directory from the previous stage
COPY --from=build /app/dist /usr/share/nginx/html

# Expose the port that Nginx is running on
EXPOSE 80


# Command to run Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
