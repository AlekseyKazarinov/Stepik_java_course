package mail_logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static final String AUSTIN_POWERS = "Austin Powers";
    public static final String WEAPONS = "weapons";
    public static final String BANNED_SUBSTANCE = "banned substance";

    /**
    Интерфейс: сущность, которую можно отправить по почте.
    У такой сущности можно получить от кого и кому направляется письмо.
    */
    public static interface Sendable {
        String getFrom();
        String getTo();
    }

    /**
    Абстрактный класс,который позволяет абстрагировать логику хранения
    источника и получателя письма в соответствующих полях класса.
    */
    public static abstract class AbstractSendable implements Sendable {

        protected final String from;
        protected final String to;

        public AbstractSendable(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AbstractSendable that = (AbstractSendable) o;

            if (!from.equals(that.from)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }
    }

    /**
    Письмо, у которого есть текст, который можно получить с помощью метода `getMessage`
    */
    public static class MailMessage extends AbstractSendable {

        private final String message;

        public MailMessage(String from, String to, String message) {
            super(from, to);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailMessage that = (MailMessage) o;

            if (message != null ? !message.equals(that.message) : that.message != null) return false;

            return true;
        }

    }

    /**
    Посылка, содержимое которой можно получить с помощью метода `getContent`
    */
    public static class MailPackage extends AbstractSendable {
        private final Package content;

        public MailPackage(String from, String to, Package content) {
            super(from, to);
            this.content = content;
        }

        public Package getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailPackage that = (MailPackage) o;

            if (!content.equals(that.content)) return false;

            return true;
        }

    }

    /**
    Класс, который задает посылку. У посылки есть текстовое описание содержимого и целочисленная ценность.
    */
    public static class Package {
        private final String content;
        private final int price;

        public Package(String content, int price) {
            this.content = content;
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Package aPackage = (Package) o;

            if (price != aPackage.price) return false;
            if (!content.equals(aPackage.content)) return false;

            return true;
        }
    }

    /*
Интерфейс, который задает класс, который может каким-либо образом обработать почтовый объект.
*/
    public static interface MailService {
        Sendable processMail(Sendable mail);
    }

    /*
    Класс, в котором скрыта логика настоящей почты
    */
    public static class RealMailService implements MailService {

        @Override
        public Sendable processMail(Sendable mail) {
            // Здесь описан код настоящей системы отправки почты.
            return mail;
        }
    }

    /**
     * Моделирует ненадёжного работника почты, который вместо того, чтобы передать почтовый
     * объект непосредственно в сервис почты, последовательно передает этот объект набору
     * третьих лиц, а затем, в конце концов, передает получившийся объект непосредственно
     * экземпляру RealMailService.
     */
    public static class UntrustworthyMailWorker implements MailService {
        private MailService[] mailServices;
        private RealMailService realMailService;
        public UntrustworthyMailWorker(MailService[] mailServices) {
            this.mailServices = mailServices;
            this.realMailService = new RealMailService();
        }

        public RealMailService getRealMailService() {
            return this.realMailService;
        }

        public Sendable processMail(Sendable mail) {
            Sendable temp = mail;
            for (MailService mailService : this.mailServices) {
                temp = mailService.processMail(temp);
            }
            return this.getRealMailService().processMail(temp);
        }

    }

    /**
     *  шпион, который логгирует о всей почтовой переписке, которая проходит
     *  через его руки. Объект конструируется от экземпляра Logger, с помощью
     *  которого шпион будет сообщать о всех действиях. Он следит только за
     *  объектами класса MailMessage и пишет в логгер следующие сообщения
     *  (в выражениях нужно заменить части в фигурных скобках на значения
     *  полей почты)
     */
    public static class Spy implements MailService {
        Logger logger;
        public Spy(Logger logger) {
            this.logger = logger;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailMessage) {
                if ((mail.getFrom().equals(AUSTIN_POWERS) ||
                        mail.getTo().equals(AUSTIN_POWERS)) &&
                        mail instanceof MailMessage) {
                    this.logger.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1} \"{2}\"",
                            new Object[]{mail.getFrom(), mail.getTo(), ((MailMessage) mail).getMessage()});
                } else {
                    this.logger.log(Level.INFO, "Usual correspondence: from {0} to {1}",
                            new Object[]{mail.getFrom(), mail.getTo()});
                }
            }
            return mail;
        }
    }

    /**
     * Вор, который ворует самые ценные посылки и игнорирует все остальное.
     */
    public static class Thief implements MailService {
        private int stolenValue = 0;
        private final int minPrice;
        /**
        @param minPrice минимальная стоимость посылки, которую будет воровать
         */
        public Thief(int minPrice) {
            this.minPrice = minPrice;
        }

        /**
         * @return суммарная стоимость сворованных посылок
         */
        public int getStolenValue() {
            return stolenValue;
        }

        public int getMinPrice() {
            return minPrice;
        }

        /**
         * вместо посылки, которая пришла вору, он отдает новую, такую же,
         * только с нулевой ценностью и содержимым посылки
         * "stones instead of {content}"
         * @param mail
         * @return
         */
        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                Package pack = ((MailPackage) mail).getContent();
                if (pack.getPrice() >= this.getMinPrice()) {
                    this.stolenValue += pack.getPrice();
                    Package newPack = new Package(String.format("stones instead of %s", pack.getContent()), 0);
                    mail = new MailPackage(mail.getFrom(), mail.getTo(), newPack);
                }
            }
            return mail;
        }
    }



    public static class IllegalPackageException extends RuntimeException {

    }

    public static class StolenPackageException extends RuntimeException {

    }



    /**
     *  Инспектор следит за запрещёнными и украденными посылками,
     *  бьёт тревогу в виде исключения, если была обнаружена подобная
     *  посылка.
     *  Если он заметил запрещённую посылку с одним из запредённых
     *  содержимым ("weapons", "banned substance"), то он бросает
     *  IllegalPackageException. Если он находит посылку из камней
     *  (содержит слово "stones"), то тревога прозвучит в виде
     *  StolenPackageException.
     */
    public static class Inspector implements MailService {
        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                Package pack = ((MailPackage) mail).getContent();
                if (pack.getContent().equals(WEAPONS) ||
                    pack.getContent().equals(BANNED_SUBSTANCE)) {
                    throw new IllegalPackageException();
                }
                if (pack.getContent().contains("stones")) {
                    throw new StolenPackageException();
                }
            }
            return mail;
        }
    }

}

