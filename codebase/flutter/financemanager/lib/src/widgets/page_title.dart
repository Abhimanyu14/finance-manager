import 'package:flutter/material.dart';

class PageTitle extends StatelessWidget {
  const PageTitle({
    Key key,
    @required this.title,
  }) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        vertical: 32.0,
        horizontal: 56.0,
      ),
      child: Text(
        title,
        // ignore: todo
        // TODO: TO resolve Google font depedencies
        // style: GoogleFonts.lato(
        //   textStyle: TextStyle(
        //     fontSize: 32.0,
        //   ),
        //   fontStyle: FontStyle.normal,
        // ),
        style: TextStyle(
          fontSize: 32.0,
        ),
      ),
    );
  }
}
