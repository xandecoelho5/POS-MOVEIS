import 'dart:io';

import 'package:dio/dio.dart';
import 'package:dio/io.dart';
import 'package:flutter/material.dart';
import 'package:nfc_sync/dao/quote_dao.dart';
import 'package:nfc_sync/database.dart';
import 'package:nfc_sync/providers/quote_provider.dart';
import 'package:nfc_sync/screens/home_screen.dart';
import 'package:nfc_sync/screens/quotes_screen.dart';
import 'package:nfc_sync/services/nfc_service.dart';
import 'package:nfc_sync/services/quote_service.dart';
import 'package:provider/provider.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  final database =
      await $FloorAppDatabase.databaseBuilder('app_database.db').build();
  final quoteDao = database.quoteDao;

  runApp(MyApp(quoteDao));
}

class MyApp extends StatelessWidget {
  const MyApp(this.quoteDao, {super.key});

  final QuoteDao quoteDao;

  @override
  Widget build(BuildContext context) {
    final dio = Dio();
    dio.httpClientAdapter = IOHttpClientAdapter(
      createHttpClient: () {
        final HttpClient client =
            HttpClient(context: SecurityContext(withTrustedRoots: false));
        client.badCertificateCallback = (cert, host, port) => true;
        return client;
      },
    );
    return MultiProvider(
      providers: [
        Provider<QuoteDao>(create: (_) => quoteDao),
        Provider<QuoteService>(create: (_) => QuoteService(dio)),
        Provider<NFCService>(create: (_) => NFCService()),
        ChangeNotifierProvider(
          create: (context) => QuoteProvider(
            context.read<QuoteService>(),
            context.read<NFCService>(),
            quoteDao,
          ),
        ),
      ],
      child: MaterialApp(
        title: 'NFC Motivational Quotes',
        theme: ThemeData(
          primarySwatch: Colors.blue,
          visualDensity: VisualDensity.adaptivePlatformDensity,
        ),
        routes: {
          '/': (context) => const HomeScreen(),
          '/quotes': (context) => const QuotesScreen(),
        },
        initialRoute: '/',
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}
