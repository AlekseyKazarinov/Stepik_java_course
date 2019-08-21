public class Robot {
    /**
     * Подключение к роботу
     */
    public interface RobotConnection extends AutoCloseable {
        void moveRobotTo(int x, int y);
        @Override
        void close();
    }

    /**
     * Установка соединения
     */
    public interface RobotConnectionManager {
        RobotConnection getConnection();
    }

    public static class RobotConnectionException extends RuntimeException {

        public RobotConnectionException(String message) {
            super(message);

        }

        public RobotConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        for (int i = 1; i <= 3; ) {
            boolean exceptionExists = false;
            try {
                RobotConnection rc = robotConnectionManager.getConnection();
                try {
                    rc.moveRobotTo(toX, toY);
                } catch (RobotConnectionException rce) {
                    exceptionExists = true;
                } finally {
                    try {
                        rc.close();
                    } catch (Exception e) {
                        // ignore
                    }
                }
            } catch (RobotConnectionException rce) {
                exceptionExists = true;
            }
            if (exceptionExists) {
                if (i == 3) throw new RobotConnectionException("so sad");
                i++;
                exceptionExists = false;
            } else {
                i = 4;
            }

        }
    /*
    for (int i = 1; i <= 3; i++) {
        try (RobotConnection rc = robotConnectionManager.getConnection()) {
            try {
                rc.moveRobotTo(toX, toY);
            } catch (RobotConnectionException rce) {
                rc.close();
            }
        } catch (RobotConnectionException re) {
            i = 4;
        }

    }
    */
    }


}
