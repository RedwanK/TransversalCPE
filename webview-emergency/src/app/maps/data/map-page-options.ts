/**
 * API page options
 */

import { PageOptions } from '../interfaces/page-options';

export const MapPageOptions: PageOptions =
{
  env: 'http://emergency-api.local/api',
  incident: '/incidents/unresolved/list',
  intervention: '/intervention/unresolved/list',
  sensor: '/sensors/list'
};
