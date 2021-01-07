<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210107131234 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE city (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, country VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE incident (id INT AUTO_INCREMENT NOT NULL, location_id INT DEFAULT NULL, intervention_id INT DEFAULT NULL, resolved_at DATETIME DEFAULT NULL, type LONGTEXT DEFAULT NULL, intensity DOUBLE PRECISION NOT NULL, code_incident LONGTEXT NOT NULL, INDEX IDX_3D03A11A64D218E (location_id), UNIQUE INDEX UNIQ_3D03A11A8EAE3863 (intervention_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE location (id INT AUTO_INCREMENT NOT NULL, city_id INT DEFAULT NULL, latitude DOUBLE PRECISION NOT NULL, longitude DOUBLE PRECISION NOT NULL, INDEX IDX_5E9E89CB8BAC62AF (city_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE incident ADD CONSTRAINT FK_3D03A11A64D218E FOREIGN KEY (location_id) REFERENCES location (id)');
        $this->addSql('ALTER TABLE incident ADD CONSTRAINT FK_3D03A11A8EAE3863 FOREIGN KEY (intervention_id) REFERENCES intervention (id)');
        $this->addSql('ALTER TABLE location ADD CONSTRAINT FK_5E9E89CB8BAC62AF FOREIGN KEY (city_id) REFERENCES city (id)');
        $this->addSql('ALTER TABLE intervention ADD incident_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE intervention ADD CONSTRAINT FK_D11814AB59E53FB9 FOREIGN KEY (incident_id) REFERENCES incident (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_D11814AB59E53FB9 ON intervention (incident_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE location DROP FOREIGN KEY FK_5E9E89CB8BAC62AF');
        $this->addSql('ALTER TABLE intervention DROP FOREIGN KEY FK_D11814AB59E53FB9');
        $this->addSql('ALTER TABLE incident DROP FOREIGN KEY FK_3D03A11A64D218E');
        $this->addSql('DROP TABLE city');
        $this->addSql('DROP TABLE incident');
        $this->addSql('DROP TABLE location');
        $this->addSql('DROP INDEX UNIQ_D11814AB59E53FB9 ON intervention');
        $this->addSql('ALTER TABLE intervention DROP incident_id');
    }
}
