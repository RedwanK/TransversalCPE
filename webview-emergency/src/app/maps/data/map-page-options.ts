/**
 * API page options
 */

import { PageOptions } from '../interfaces/page-options';

export const MapPageOptions: PageOptions =
{
  env: 'http://localhost/api',
  incident: '/incidents/unresolved/list',
  intervention: '/interventions/unresolved/list',
  sensor: '/sensors/list'
};
