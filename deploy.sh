#!/usr/bin/env bash

mvn azure-functions:deploy
az functionapp config appsettings set \
  --name fta-tariff-rates-extractor \
  --resource-group vangos-resources \
  --settings TARIFF_DOCS_CLIENT_ID="@Microsoft.KeyVault(SecretUri=https://vangos-key-vault.vault.azure.net/secrets/tariff-docs-client-id/a841be49c5664694b16bb0fd09c6c920)" \
  TARIFF_DOCS_CLIENT_SECRET="@Microsoft.KeyVault(SecretUri=https://vangos-key-vault.vault.azure.net/secrets/tariff-docs-client-secret/2bf2b2f9f50d4aaaa27927501c4e0a5a)" \
  TARIFF_DOCS_METADATA_URL=$TARIFF_DOCS_METADATA_URL \
  TARIFF_DOCS_ACCESS_TOKEN_URL=$TARIFF_DOCS_ACCESS_TOKEN_URL