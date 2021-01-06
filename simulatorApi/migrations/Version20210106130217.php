<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210106130217 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE location (id INT AUTO_INCREMENT NOT NULL, city_id INT DEFAULT NULL, latitude DOUBLE PRECISION NOT NULL, longitude DOUBLE PRECISION NOT NULL, INDEX IDX_5E9E89CB8BAC62AF (city_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE location ADD CONSTRAINT FK_5E9E89CB8BAC62AF FOREIGN KEY (city_id) REFERENCES city (id)');
        $this->addSql('ALTER TABLE incident DROP FOREIGN KEY FK_3D03A11A8BAC62AF');
        $this->addSql('DROP INDEX IDX_3D03A11A8BAC62AF ON incident');
        $this->addSql('ALTER TABLE incident ADD resolved_at DATETIME DEFAULT NULL, ADD type LONGTEXT DEFAULT NULL, ADD code_incident LONGTEXT NOT NULL, DROP latitude, DROP longitude, CHANGE city_id location_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE incident ADD CONSTRAINT FK_3D03A11A64D218E FOREIGN KEY (location_id) REFERENCES location (id)');
        $this->addSql('CREATE INDEX IDX_3D03A11A64D218E ON incident (location_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE incident DROP FOREIGN KEY FK_3D03A11A64D218E');
        $this->addSql('DROP TABLE location');
        $this->addSql('DROP INDEX IDX_3D03A11A64D218E ON incident');
        $this->addSql('ALTER TABLE incident ADD latitude DOUBLE PRECISION NOT NULL, ADD longitude DOUBLE PRECISION NOT NULL, DROP resolved_at, DROP type, DROP code_incident, CHANGE location_id city_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE incident ADD CONSTRAINT FK_3D03A11A8BAC62AF FOREIGN KEY (city_id) REFERENCES city (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('CREATE INDEX IDX_3D03A11A8BAC62AF ON incident (city_id)');
    }
}
