<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210106141458 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE incident ADD sensor_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE incident ADD CONSTRAINT FK_3D03A11AA247991F FOREIGN KEY (sensor_id) REFERENCES sensor (id)');
        $this->addSql('CREATE INDEX IDX_3D03A11AA247991F ON incident (sensor_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE incident DROP FOREIGN KEY FK_3D03A11AA247991F');
        $this->addSql('DROP INDEX IDX_3D03A11AA247991F ON incident');
        $this->addSql('ALTER TABLE incident DROP sensor_id');
    }
}
