import 'package:flutter/material.dart';
import 'package:nfc_sync/models/quote.dart';
import 'package:nfc_sync/providers/quote_provider.dart';
import 'package:provider/provider.dart';

import '../services/nfc_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  void initState() {
    super.initState();
    Future.delayed(Duration.zero, () {
      context.read<QuoteProvider>().setupAutoNFCQuote();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Motivational Quotes'),
        actions: [
          IconButton(
            icon: const Icon(Icons.list),
            onPressed: () => Navigator.of(context).pushNamed('/quotes'),
          ),
          IconButton(
            icon: const Icon(Icons.nfc),
            onPressed: () => context.read<NFCService>().writeQuoteTag(),
          ),
        ],
      ),
      body: Center(
        child: Consumer<QuoteProvider>(
          builder: (context, quoteProvider, child) {
            if (quoteProvider.isLoading) {
              return const CircularProgressIndicator();
            }
            if (quoteProvider.error != null) {
              return Text('Error: ${quoteProvider.error}');
            }
            if (quoteProvider.currentQuote != null) {
              return QuoteText(quoteProvider.currentQuote!);
            }
            return const Text('Hold phone near NFC tag to get a quote');
          },
        ),
      ),
    );
  }
}

class QuoteText extends StatelessWidget {
  const QuoteText(this.quote, {super.key});

  final Quote quote;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            '"${quote.content}"',
            style: const TextStyle(fontSize: 24, fontStyle: FontStyle.italic),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: 16),
          Text(
            '- ${quote.author}',
            style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
        ],
      ),
    );
  }
}
