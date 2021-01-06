<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210106162437 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE agent (id INT AUTO_INCREMENT NOT NULL, job_id INT DEFAULT NULL, team_id INT DEFAULT NULL, firstname TINYTEXT DEFAULT NULL, lastname TINYTEXT DEFAULT NULL, street TINYTEXT DEFAULT NULL, zipcode TINYTEXT DEFAULT NULL, phone_number TINYTEXT DEFAULT NULL, INDEX IDX_268B9C9DBE04EA9 (job_id), INDEX IDX_268B9C9D296CD8AE (team_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE category_vehicle (id INT AUTO_INCREMENT NOT NULL, capacity INT NOT NULL, name LONGTEXT NOT NULL, coefficient DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE job (id INT AUTO_INCREMENT NOT NULL, name TINYTEXT NOT NULL, coefficient DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE team (id INT AUTO_INCREMENT NOT NULL, name TINYTEXT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE vehicle (id INT AUTO_INCREMENT NOT NULL, category_vehicle_id INT DEFAULT NULL, team_id INT DEFAULT NULL, registration_number TINYTEXT NOT NULL, fuel LONGTEXT DEFAULT NULL, INDEX IDX_1B80E486724D47AF (category_vehicle_id), INDEX IDX_1B80E486296CD8AE (team_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE agent ADD CONSTRAINT FK_268B9C9DBE04EA9 FOREIGN KEY (job_id) REFERENCES job (id)');
        $this->addSql('ALTER TABLE agent ADD CONSTRAINT FK_268B9C9D296CD8AE FOREIGN KEY (team_id) REFERENCES team (id)');
        $this->addSql('ALTER TABLE vehicle ADD CONSTRAINT FK_1B80E486724D47AF FOREIGN KEY (category_vehicle_id) REFERENCES category_vehicle (id)');
        $this->addSql('ALTER TABLE vehicle ADD CONSTRAINT FK_1B80E486296CD8AE FOREIGN KEY (team_id) REFERENCES team (id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE vehicle DROP FOREIGN KEY FK_1B80E486724D47AF');
        $this->addSql('ALTER TABLE agent DROP FOREIGN KEY FK_268B9C9DBE04EA9');
        $this->addSql('ALTER TABLE agent DROP FOREIGN KEY FK_268B9C9D296CD8AE');
        $this->addSql('ALTER TABLE vehicle DROP FOREIGN KEY FK_1B80E486296CD8AE');
        $this->addSql('DROP TABLE agent');
        $this->addSql('DROP TABLE category_vehicle');
        $this->addSql('DROP TABLE job');
        $this->addSql('DROP TABLE team');
        $this->addSql('DROP TABLE vehicle');
    }
}
