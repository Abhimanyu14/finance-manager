import 'package:financemanager/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  group('Testing App Performance Tests', () {
    final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized()
        as IntegrationTestWidgetsFlutterBinding;

    binding.framePolicy = LiveTestWidgetsFlutterBindingFramePolicy.fullyLive;

    testWidgets('Testing navigation to add transaction page', (tester) async {
      await tester.pumpWidget(MyApp());
      expect(find.text('My Transactions'), findsOneWidget);

      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      expect(find.text('Enter Transaction Details'), findsOneWidget);
      expect(find.text('My Transactions'), findsNothing);
    });
  });
}
