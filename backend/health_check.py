import json
import psutil
from flask import Flask, jsonify
from sqlalchemy.exc import OperationalError, SQLAlchemyError
from backend.database import db_session  # Assuming this is the database session from the codebase
import os

app = Flask(__name__)

class HealthCheck:
    @staticmethod
    def check_database():
        """Check database connectivity."""
        try:
            # Perform a simple query to check database health
            db_session.execute("SELECT 1")
            return {"database": "healthy"}
        except (OperationalError, SQLAlchemyError) as e:
            return {"database": "unhealthy", "error": str(e)}

    @staticmethod
    def check_system_resources():
        """Check system resource usage."""
        cpu_usage = psutil.cpu_percent(interval=1)
        memory = psutil.virtual_memory()
        memory_usage = memory.percent

        # Define thresholds for healthy resource usage (configurable via environment variables)
        cpu_threshold = int(os.getenv("CPU_THRESHOLD", 80))  # Default: 80% CPU usage
        memory_threshold = int(os.getenv("MEMORY_THRESHOLD", 85))  # Default: 85% memory usage

        resource_status = {
            "cpu_usage": f"{cpu_usage}%",
            "memory_usage": f"{memory_usage}%",
            "status": "healthy" if cpu_usage < cpu_threshold and memory_usage < memory_threshold else "unhealthy"
        }
        return resource_status

    @staticmethod
    def run_health_check():
        """Run all health checks and return the results."""
        database_status = HealthCheck.check_database()
        system_status = HealthCheck.check_system_resources()

        # Combine results
        overall_status = "healthy" if database_status["database"] == "healthy" and system_status["status"] == "healthy" else "unhealthy"
        return {
            "status": overall_status,
            "details": {
                "database": database_status["database"],
                "system_resources": system_status
            }
        }

@app.route('/health', methods=['GET'])
def health_check():
    """Health check endpoint."""
    health_status = HealthCheck.run_health_check()
    # Return 200 if healthy, 503 if unhealthy
    status_code = 200 if health_status["status"] == "healthy" else 503
    return jsonify(health_status), status_code

if __name__ == "__main__":
    # Run the Flask app
    app.run(host="0.0.0.0", port=5000)