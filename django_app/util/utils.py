from django.core.management.base import BaseCommand


class SimpleBaseCommand(BaseCommand):
    def _log_success(self, msg):
        self.stdout.write(
            self.style.SUCCESS(msg)
        )

    def _log_info(self, msg):
        self.stdout.write(
            self.style.NOTICE(msg)
        )

    def _log_warning(self, msg):
        self.stdout.write(
            self.style.WARNING(msg)
        )

    def _log_error(self, msg):
        self.stdout.write(
            self.style.ERROR(msg)
        )
